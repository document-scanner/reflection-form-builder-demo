/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.richtercloud.reflection.form.builder.demo;

import javax.persistence.Entity;

/**
 *
 * @author richter
 */
@Entity
public class EntityC extends EntityA {
    private static final long serialVersionUID = 1L;
    private String stringBasicC;

    protected EntityC() {
        super();
    }

    public EntityC(Long id, int intBasicA, String stringBasicA, String stringBasicC) {
        super(id, intBasicA, stringBasicA);
        this.stringBasicC = stringBasicC;
    }

    /**
     * @return the basicString
     */
    public String getStringBasicC() {
        return this.stringBasicC;
    }

    /**
     * @param stringBasicC the basicString to set
     */
    public void setStringBasicC(String stringBasicC) {
        this.stringBasicC = stringBasicC;
    }

    @Override
    public String toString() {
        return this.getReflectionToStringBuilder().toString();
    }
}
