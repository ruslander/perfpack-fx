data
---------------------------------------------------------------------------------------------------------------------------------

run 
11/09/2019 12:53:45 pm to 11/09/2019 01:00:51 pm

https://app.us1.signalfx.com/#/dashboard/EILEhK_A0AE?groupId=EH-5ty4A4AI&configId=EILEhLQA4AA&density=2&startTimeUTC=1573322025000&endTimeUTC=1573322451000




fetch metrics
--------------------------------------------------------------------------------------------------------------------------------

export TKN=https://app.us1.signalfx.com/#/myprofile

curl -H "X-SF-TOKEN: $TKN" \
-H "Content-Type: application/json" \
'https://api.us1.signalfx.com/v1/timeserieswindow?query=sf_metric:"concurrencyLevel"&startMs=1573322025000&endMs=1573322451000'

curl -H "X-SF-TOKEN: $TKN" \
-H "Content-Type: application/json" \
'https://api.us1.signalfx.com/v1/timeserieswindow?query=sf_metric:"responseTime.mean"&startMs=1573322025000&endMs=1573322451000'

curl -H "X-SF-TOKEN: $TKN" \
-H "Content-Type: application/json" \
'https://api.us1.signalfx.com/v1/timeserieswindow?query=sf_metric:"throughput.count"&startMs=1573322025000&endMs=1573322451000'


bench
--------------------------------------------------------------------------------------------------------------------------------

concise run/parse/report
https://github.com/mookerji/sca_tools/blob/master/examples/sysbench/cpu.sh
