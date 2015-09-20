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

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author richter
 */
@Entity
public class EntityA implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private int a;
    private String b;
    @ElementCollection
    private List<Integer> cs = new LinkedList<>(Arrays.asList(1));
    @OneToMany
    private List<EntityB> entityBs = new LinkedList<>(Arrays.asList(new EntityB()));

    protected EntityA() {
    }

    public EntityA(Long id, int a, String b) {
        this.id = id;
        this.a = a;
        this.b = b;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the a
     */
    public int getA() {
        return this.a;
    }

    /**
     * @param a the a to set
     */
    public void setA(int a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public String getB() {
        return this.b;
    }

    /**
     * @param b the b to set
     */
    public void setB(String b) {
        this.b = b;
    }

    /**
     * @return the cs
     */
    public List<Integer> getCs() {
        return cs;
    }

    /**
     * @param cs the cs to set
     */
    public void setCs(List<Integer> cs) {
        this.cs = cs;
    }

    /**
     * @return the entityBs
     */
    public List<EntityB> getEntityBs() {
        return entityBs;
    }

    /**
     * @param entityBs the entityBs to set
     */
    public void setEntityBs(List<EntityB> entityBs) {
        this.entityBs = entityBs;
    }
}
