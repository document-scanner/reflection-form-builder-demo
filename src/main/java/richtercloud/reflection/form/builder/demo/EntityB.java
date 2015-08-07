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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author richter
 */
@Entity
public class EntityB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private int a;
    @OneToOne
    private EntityA entityA;

    protected EntityB() {
    }

    public EntityB(Long id, int a, EntityA entityA) {
        this.id = id;
        this.a = a;
        this.entityA = entityA;
    }

    public EntityA getEntityA() {
        return this.entityA;
    }

    public void setEntityA(EntityA entityA) {
        this.entityA = entityA;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
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
}
