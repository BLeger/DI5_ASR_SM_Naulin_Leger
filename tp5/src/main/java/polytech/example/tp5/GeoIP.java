package polytech.example.tp5;

import java.io.Serializable;

public class GeoIP implements Serializable {
    private String status;
    private String query;
    private String country;
    private String countryCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GeoIP : [status = ");
        builder.append(status);
        builder.append(" query = ");
        builder.append(query);
        builder.append(" country = ");
        builder.append(country);
        builder.append(" countryCode = ");
        builder.append(countryCode);
        builder.append("]");

        return builder.toString();
    }
}
