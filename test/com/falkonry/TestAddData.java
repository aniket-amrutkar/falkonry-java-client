package com.falkonry;

import com.falkonry.client.Falkonry;
import com.falkonry.helper.models.Eventbuffer;
import com.falkonry.helper.models.InputStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/*!
 * falkonry-java-client
 * Copyright(c) 2016 Falkonry Inc
 * MIT Licensed
 */

public class TestAddData {
    Falkonry falkonry = null;
    String host = "https://dev.falkonry.io";
    String token = "f8xew97kv5rc1qdgps0hn5qi77om014q";
    List<Eventbuffer> eventbuffers = new ArrayList<Eventbuffer>();

    @Before
    public void setUp() throws Exception {
        falkonry = new Falkonry(host, token);
    }

    @Test
    public void addDataJson() throws Exception {
        Eventbuffer eb = new Eventbuffer();
        eb.setName("Test-EB-" + Math.random());
        Map<String, String> options = new HashMap<String, String>();
        options.put("timeIdentifier", "time");
        options.put("timeFormat", "iso_8601");
        Eventbuffer eventbuffer = falkonry.createEventbuffer(eb, options);
        eventbuffers.add(eventbuffer);
        String data = "{\"time\" :\"2016-03-01 01:01:01\", \"current\" : 12.4, \"vibration\" : 3.4, \"state\" : \"On\"}";
        options.put("fileFormat","json");
        InputStatus inputStatus = falkonry.addInput(eventbuffer.getId(),data,options);
        eventbuffer = falkonry.getUpdatedEventbuffer(eventbuffer.getId());
        Assert.assertEquals(1,eventbuffer.getSchemaList().size());
    }

    @Test
    public void addDataCsv() throws Exception {
        Eventbuffer eb = new Eventbuffer();
        eb.setName("Test-EB-" + Math.random());
        Map<String, String> options = new HashMap<String, String>();
        options.put("timeIdentifier", "time");
        options.put("timeFormat", "iso_8601");
        Eventbuffer eventbuffer = falkonry.createEventbuffer(eb, options);
        eventbuffers.add(eventbuffer);
        String data = "time, current, vibration, state\n" + "2016-03-01 01:01:01, 12.4, 3.4, On";
        options.put("fileFormat","csv");
        InputStatus inputStatus = falkonry.addInput(eventbuffer.getId(),data,options);
        eventbuffer = falkonry.getUpdatedEventbuffer(eventbuffer.getId());
        Assert.assertEquals(1,eventbuffer.getSchemaList().size());
    }

    @After
    public void cleanUp() throws Exception {
        Iterator<Eventbuffer> itr = eventbuffers.iterator();
        while(itr.hasNext()) {
            Eventbuffer eb = itr.next();
            falkonry.deleteEventbuffer(eb.getId());
        }
    }
}
