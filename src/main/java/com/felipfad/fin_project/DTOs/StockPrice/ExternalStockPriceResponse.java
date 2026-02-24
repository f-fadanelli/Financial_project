package com.felipfad.fin_project.DTOs.StockPrice;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExternalStockPriceResponse {

    @JsonProperty("Time Series (Daily)")
    private Map<String, DailyData> timeSeries;

    public Map<String, DailyData> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, DailyData> timeSeries) {
        this.timeSeries = timeSeries;
    }

    public static class DailyData {

        @JsonProperty("1. open")
        private String open;

        @JsonProperty("2. high")
        private String high;

        @JsonProperty("3. low")
        private String low;

        @JsonProperty("4. close")
        private String close;

        @JsonProperty("5. volume")
        private String volume;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }
    }
}
