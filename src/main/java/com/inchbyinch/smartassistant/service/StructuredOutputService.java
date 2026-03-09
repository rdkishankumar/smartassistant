package com.inchbyinch.smartassistant.service;

import com.inchbyinch.smartassistant.model.CountryCities;

import java.util.List;
import java.util.Map;

public interface StructuredOutputService {

    CountryCities structuredOutput(String  message);

    List<String> chatListOutput(String  message);
    Map<String,Object> chatMapOutput(String  message);
}
