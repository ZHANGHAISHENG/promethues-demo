vi /etc/prometheus/prometheus.yml 
增加配置

- job_name: client-test
    target_groups:
      - targets: ['192.168.1.101:3300']
	  
	  
promsql测试：
sum(irate(http_request_latency_count[10m])) by (api)
jvm_memory_bytes_used{job="client-test", area="heap"}
histogram_quantile(0.99, sum(rate(http_request_latency_bucket{job="client-test"}[10m])) by (api, le))	  