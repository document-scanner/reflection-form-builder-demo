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
public class EntityD implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @OneToMany
    private List<EntityC> oneToManyEntityCs;
    private String stringBasic;
    @ElementCollection
    private List<EmbeddableA> elementCollectionEmbeddables = new LinkedList<>();
    private transient final ReflectionToStringBuilder reflectionToStringBuilder;

    protected EntityD() {
        this.reflectionToStringBuilder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public EntityD(Long id, List<EntityC> oneToManyEntityCs, String stringBasic) {
        this();
        this.id = id;
        this.oneToManyEntityCs = oneToManyEntityCs;
        this.stringBasic = stringBasic;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the oneToManyEntityCs
     */
    public List<EntityC> getOneToManyEntityCs() {
        return oneToManyEntityCs;
    }

    /**
     * @param oneToManyEntityCs the oneToManyEntityCs to set
     */
    public void setOneToManyEntityCs(List<EntityC> oneToManyEntityCs) {
        this.oneToManyEntityCs = oneToManyEntityCs;
    }

    /**
     * @return the stringBasic
     */
    public String getStringBasicD() {
        return stringBasic;
    }

    /**
     * @param d the stringBasic to set
     */
    public void setStringBasicD(String d) {
        this.stringBasic = d;
    }

    public List<EmbeddableA> getElementCollectionEmbeddables() {
        return elementCollectionEmbeddables;
    }

    public void setElementCollectionEmbeddables(List<EmbeddableA> elementCollectionEmbeddables) {
        this.elementCollectionEmbeddables = elementCollectionEmbeddables;
    }

    @Override
    public String toString() {
        return this.reflectionToStringBuilder.toString();
    }

}
