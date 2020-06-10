package CruxHelper;

import Interfaces.GenericIDComponents;

import java.util.Arrays;

public class CruxId {

    private final String[] VALID_CRUX_NAMESPACE = new String[]{"crux", "local"};

    public GenericIDComponents fromString(String stringRepresentation) throws Exception {
        stringRepresentation = stringRepresentation.toLowerCase();
        String[] arrayCruxId = stringRepresentation.split("\\s*[\\.,@]+\\s*");

        if (arrayCruxId.length == 3) {

            if (!Arrays.asList(this.VALID_CRUX_NAMESPACE).contains(arrayCruxId[2])) {
                throw new Exception("Invalid Crux Id Namespace");
            }

            return new GenericIDComponents().setSubdomain(arrayCruxId[0])
                    .setDomain(arrayCruxId[1])
                    .setNamespace(arrayCruxId[2]);
        } else {
            throw new Exception("CruxId Invalid Structure");
        }

    }

    public String toBlockStackString(GenericIDComponents components) {
        return components.getSubdomain() + "." + components.getDomain() + "." + components.getNamespace();
    }

    public String toString(GenericIDComponents components) {
        return components.getSubdomain() + "@" + components.getDomain() + "." + components.getNamespace();
    }
}
