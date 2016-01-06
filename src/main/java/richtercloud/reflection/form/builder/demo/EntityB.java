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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author richter
 */
@Entity
public class EntityB implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private int intBasicB;
    @OneToOne
    private EntityA oneToOneEntityA;
    private transient final ReflectionToStringBuilder reflectionToStringBuilder;

    protected EntityB() {
        this.reflectionToStringBuilder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public EntityB(Long id, int intBasicB, EntityA oneToOneEntityA) {
        this();
        this.id = id;
        this.intBasicB = intBasicB;
        this.oneToOneEntityA = oneToOneEntityA;
    }

    public EntityA getOneToOneEntityA() {
        return this.oneToOneEntityA;
    }

    public void setOneToOneEntityA(EntityA oneToOneEntityA) {
        this.oneToOneEntityA = oneToOneEntityA;
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
     * @return the intBasicB
     */
    public int getIntBasicB() {
        return this.intBasicB;
    }

    /**
     * @param intBasicB the intBasicB to set
     */
    public void setIntBasicB(int intBasicB) {
        this.intBasicB = intBasicB;
    }

    public ReflectionToStringBuilder getReflectionToStringBuilder() {
        return reflectionToStringBuilder;
    }

    @Override
    public String toString() {
        return this.reflectionToStringBuilder.toString();
    }
}
