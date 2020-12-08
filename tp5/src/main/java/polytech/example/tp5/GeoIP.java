package polytech.example.tp5;

import java.io.Serializable;

public class GeoIP implements Serializable {
    private String status;
    private String query;
    private String country;
    private String countryCode;
    private String region;
    private String zip;
    private String city;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        builder.append(" region = ");
        builder.append(region);
        builder.append(" zip = ");
        builder.append(zip);
        builder.append(" city = ");
        builder.append(city);
        builder.append("]");

        return builder.toString();
    }
}
