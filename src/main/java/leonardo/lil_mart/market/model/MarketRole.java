package leonardo.lil_mart.market.model;

public enum MarketRole {
    ADMIN("admin"),
    MARKET("market");

    private String role;

    MarketRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
