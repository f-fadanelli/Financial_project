package com.felipfad.fin_project.data.consumer;

import java.util.Map;

public interface PortfolioSnapshotListenerInterface {

    void handlePriceUpdate(Map<String, String> message);

}

