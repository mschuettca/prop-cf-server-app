package com.msapps.property.rental;

public class TransactionCostSum {
    private String _id;
    private double total;

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return "CostSum{" +
                "Type='" + this._id + '\'' +
                "total=" + this.total + "}";
    }
}
