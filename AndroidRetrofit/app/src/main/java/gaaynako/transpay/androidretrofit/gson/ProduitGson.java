package gaaynako.transpay.androidretrofit.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProduitGson {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("cost")
    @Expose
    private Double cost;

    @SerializedName("category_id")
    @Expose
    private int category;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
