package com.didispace.web;

import com.didispace.service.SqlTestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ComputeController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SqlTestService sqlTestService;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public String add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return "From Service-A, Result is " + r;
    }

    //A服务调用B服务
    @RequestMapping(value="testServiceB",method=RequestMethod.GET)
    public String testServiceB(@RequestParam Integer a,@RequestParam Integer b){
    	RestTemplate restTemplate=new RestTemplate();
    	return restTemplate.getForObject("http://localhost:3333/add?a="+a+"&b="+b, String.class);
    }


    @RequestMapping(value="/SqlServer",method=RequestMethod.GET)
    public String getList() {
        return sqlTestService.getList();
    }

    @RequestMapping(value="/update",method=RequestMethod.GET)
    public String update() {
        return sqlTestService.update();
    }
    
}