package com.example.vladislavnedyalkovemployees.dtos;

import com.example.vladislavnedyalkovemployees.entities.PairProjectWorkDuration;

import java.util.List;

public class PairProjectResponse {

    private List<PairProjectWorkDuration> pairProjectWorkDurations;

    private String errorMessage;

    public PairProjectResponse(List<PairProjectWorkDuration> pairProjectWorkDurations, String errorMessage) {
        this.pairProjectWorkDurations = pairProjectWorkDurations;
        this.errorMessage = errorMessage;
    }

    public List<PairProjectWorkDuration> getPairProjectWorkDurations() {
        return pairProjectWorkDurations;
    }

    public void setPairProjectWorkDurations(List<PairProjectWorkDuration> pairProjectWorkDurations) {
        this.pairProjectWorkDurations = pairProjectWorkDurations;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
