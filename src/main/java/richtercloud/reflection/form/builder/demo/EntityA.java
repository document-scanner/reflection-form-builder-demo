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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author richter
 */
@Entity
public class EntityA implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private int intBasic;
    private String stringBasic;
    @ElementCollection
    private List<Integer> elementCollectionBasics = new LinkedList<>(Arrays.asList(1));
    @OneToMany
    private List<EntityB> oneToManyEntityBs = new LinkedList<>();
    private transient final ReflectionToStringBuilder reflectionToStringBuilder;

    protected EntityA() {
        this.reflectionToStringBuilder = new ReflectionToStringBuilder(this);
    }

    public EntityA(Long id, int intBasic, String stringBasic) {
        this();
        this.id = id;
        this.intBasic = intBasic;
        this.stringBasic = stringBasic;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the intBasic
     */
    public int getIntBasic() {
        return this.intBasic;
    }

    /**
     * @param a the intBasic to set
     */
    public void setIntBasic(int a) {
        this.intBasic = a;
    }

    /**
     * @return the stringBasic
     */
    public String getStringBasic() {
        return this.stringBasic;
    }

    /**
     * @param b the stringBasic to set
     */
    public void setStringBasic(String b) {
        this.stringBasic = b;
    }

    /**
     * @return the elementCollectionBasics
     */
    public List<Integer> getElementCollectionBasics() {
        return elementCollectionBasics;
    }

    /**
     * @param cs the elementCollectionBasics to set
     */
    public void setElementCollectionBasics(List<Integer> cs) {
        this.elementCollectionBasics = cs;
    }

    /**
     * @return the oneToManyEntityBs
     */
    public List<EntityB> getOneToManyEntityBs() {
        return oneToManyEntityBs;
    }

    /**
     * @param entityBs the oneToManyEntityBs to set
     */
    public void setOneToManyEntityBs(List<EntityB> entityBs) {
        this.oneToManyEntityBs = entityBs;
    }

    public ReflectionToStringBuilder getReflectionToStringBuilder() {
        return reflectionToStringBuilder;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
    }
}
