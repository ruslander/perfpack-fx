require 'sinatra'
require "sinatra/json"
require 'open3'
require 'pp'
require 'json'
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
	begin
	  	tableArgs = params[:data] 
		eval_usl(tableArgs)
	rescue => error
		pp error.message
    	halt 500  , error.message
	end
end	

def eval_usl(table)
	model = IO.read("./models/usl-calculator.r")

	model = model
		.gsub("\t", "")	
		.gsub("TAB_SPEC_NX", table)	

	error = ""

	Open3.popen3("Rscript -e '#{model}'") do |stdin, stdout, stderr, wait_thr|
	  error = stderr.read
	end

	if error.nil? || error.empty?
		return Base64.encode64(IO.read("/tmp/rplot-file.jpg"))
	else
		raise error
	end
end
