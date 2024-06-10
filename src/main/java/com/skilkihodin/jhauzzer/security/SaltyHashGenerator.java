package com.skilkihodin.jhauzzer.security;

public abstract class SaltyHashGenerator extends HashGenerator {
    protected final String salt;


    public SaltyHashGenerator(String salt) {
        this.salt = salt;
    }


    @Override
    public int hashCode() {
        return salt.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass()) {
            SaltyHashGenerator other = (SaltyHashGenerator)o;

            return this.salt.equals(other.salt);
        }

        return false;
    }
}
