require 'sinatra'
require "sinatra/json"
require 'open3'
require 'pp'
require 'uri'
require 'json'
require 'enumerator'
require 'base64'

set :protection, except: [ :json_csrf ]

port = ENV['PORT'] || 8080
puts "STARTING SINATRA on port #{port}"
set :port, port
set :bind, '0.0.0.0'

get '/' do
  File.read(File.join('public', 'index.html'))
end


post '/usl' do
  	table = params[:data] 
  	
  	#pp table

  	eval_model(table)
  	
	data = IO.read("/tmp/rplot-file.jpg")
	Base64.encode64(data)
end	



def eval_model(table)
	result = ""
	error = ""

	model = IO.read("./usl-calculator.r")

	model = model
		.gsub("\t", "")	
		.gsub("TAB_SPEC_NX", table)	

	path_to_file = "/tmp/rplot-file.jpg"
	#File.delete(path_to_file) if File.exist?(path_to_file)

	Open3.popen3("Rscript --verbose -e '#{model}'") do |stdin, stdout, stderr, wait_thr|
	  result = stdout.read
	  error = stderr.read
	end

	if error.nil? || error.empty?
  		return result
	else
		pp "!!!! #{error}"
		return error
	end
end
