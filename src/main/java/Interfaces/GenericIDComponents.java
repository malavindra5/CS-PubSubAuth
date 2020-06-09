package Interfaces;

public class GenericIDComponents {

    private String domain;

    private String subdomain;

    private String namespace;

    public String getDomain() {
        return domain;
    }

    public GenericIDComponents setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public GenericIDComponents setSubdomain(String subdomain) {
        this.subdomain = subdomain;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public GenericIDComponents setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
}
