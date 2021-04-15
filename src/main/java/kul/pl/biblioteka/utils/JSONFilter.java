package kul.pl.biblioteka.utils;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.Arrays;
import java.util.List;

public class JSONFilter {
    private MappingJacksonValue mapper;

    public MappingJacksonValue getMapper() {
        return mapper;
    }

    public static class Builder{
        private Object object;
        private SimpleBeanPropertyFilter bean;
        private FilterProvider provider;

        public Builder(Object object){
            this.object = object;
        }

        public Builder exclude(String ... exclude){
            this.bean = SimpleBeanPropertyFilter.serializeAllExcept(exclude);
            return this;
        }

        public Builder setFilter(String filter){
            if(bean == null) throw new RuntimeJsonMappingException("You have to use exclude method before setting filter");
            provider = new SimpleFilterProvider().addFilter(filter, bean);
            return this;
        }

        public JSONFilter build(){
            JSONFilter jsonFilter = new JSONFilter();
            jsonFilter.mapper = new MappingJacksonValue(object);
            jsonFilter.mapper.setFilters(provider);
            return jsonFilter;
        }
    }


}
