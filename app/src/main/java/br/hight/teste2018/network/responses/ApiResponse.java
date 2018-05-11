package br.hight.teste2018.network.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Thiago Azevedo on 28/04/2018.
 */

public class ApiResponse {

    @SerializedName("page")
    Integer page;

    @SerializedName("total_results")
    Integer totalResults;

    @SerializedName("total_pages")
    Integer totalPages;

    @SerializedName("results")
    ArrayList<ResultsResponse> results;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public ArrayList<ResultsResponse> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultsResponse> results) {
        this.results = results;
    }
}
