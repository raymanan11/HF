/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.fabcar;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class AirlinePart {

    @Property()
    private final String name;

    @Property()
    private final boolean isDefect;

    @Property()
    private final String serialNumber;

    @Property()
    private final String owner;

    public String getName() {
        return name;
    }

    public boolean getIsDefect() {
        return isDefect;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getOwner() {
        return owner;
    }

    public AirlinePart(@JsonProperty("name") final String name, @JsonProperty("isDefect") final boolean isDefect,
            @JsonProperty("serialNumber") final String serialNumber, @JsonProperty("owner") final String owner) {
        this.name = name;
        this.isDefect = isDefect;
        this.serialNumber = serialNumber;
        this.owner = owner;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        AirlinePart other = (AirlinePart) obj;

        return Objects.deepEquals(new Object[] {getName(), getIsDefect(), getSerialNumber(), getOwner()},
                new Object[] {other.getName(), other.getIsDefect(), other.getSerialNumber(), other.getOwner()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getIsDefect(), getSerialNumber(), getOwner());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [name=" + name + ", isDefect="
                + isDefect + ", serialNumber=" + serialNumber + ", owner=" + owner + "]";
    }
}
