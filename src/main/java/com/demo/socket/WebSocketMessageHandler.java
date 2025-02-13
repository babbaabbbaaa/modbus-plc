package com.demo.socket;


import com.demo.model.PLCSearchCriteria;
import com.demo.model.Response;
import com.demo.plc.IDataSearchService;
import com.demo.plc.IPLCData;
import com.demo.utility.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import jakarta.websocket.Session;

@Getter
@Setter
public class WebSocketMessageHandler {

    private PLCSearchCriteria criteria;

    private final IDataSearchService dataSearchService;
    private final Session session;

    public WebSocketMessageHandler(IDataSearchService dataSearchService, Session session) {
        this.dataSearchService = dataSearchService;
        this.session = session;
        this.criteria = null;
    }


    public void onMessage(String message) {
        this.criteria = JsonUtil.readObject(message, PLCSearchCriteria.class);
        sendToClients();
    }

    public void sendToClients() {
        Page<? extends IPLCData> results = dataSearchService.search(criteria);
        session.getAsyncRemote().sendObject(Response.success(results));
    }
}
