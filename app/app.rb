require 'sinatra'
require "sinatra/json"
require 'open3'
require 'pp'
require 'uri'
require 'json'
require 'enumerator'

set :protection, except: [ :json_csrf ]

port = ENV['PORT'] || 8080
puts "STARTING SINATRA on port #{port}"
set :port, port
set :bind, '0.0.0.0'

get '/' do
  File.read(File.join('public', 'index.html'))
end


post '/chart' do
	content_type :json

  	model = params[:model] 
  	
  	report = eval_model(model)
  	result = report_to_charts_data(report)
  	
	result.to_json
end	
