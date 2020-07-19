package com.phantomblood.jojo.service.Impl;

import com.phantomblood.jojo.mapper.testmapper;
import com.phantomblood.jojo.service.testService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class testServiceImpl implements testService {

    @Resource
    private testmapper testmapper;

    @Override
    public Map findUserById(Map map) {
        return testmapper.selectJojo(map);
    }
}
