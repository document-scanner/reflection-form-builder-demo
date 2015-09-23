/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package richtercloud.reflection.form.builder.demo;

import javax.persistence.Entity;

/**
 *
 * @author richter
 */
@Entity
public class EntityC extends EntityA {
    private static final long serialVersionUID = 1L;
    private String d;

    protected EntityC() {
    }

    public EntityC(Long id, int a, String b, String d) {
        super(id, a, b);
        this.d = d;
    }

    /**
     * @return the d
     */
    public String getD() {
        return this.d;
    }

    /**
     * @param d the d to set
     */
    public void setD(String d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return this.getReflectionToStringBuilder().toString();
    }
}
