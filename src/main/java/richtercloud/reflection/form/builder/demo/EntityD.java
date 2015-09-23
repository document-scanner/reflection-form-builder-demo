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
import java.util.List;
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
    private List<EntityC> entityCs;
    private String d;
    private transient final ReflectionToStringBuilder reflectionToStringBuilder;

    protected EntityD() {
        this.reflectionToStringBuilder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public EntityD(Long id, List<EntityC> entityCs, String d) {
        this();
        this.id = id;
        this.entityCs = entityCs;
        this.d = d;
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
     * @return the entityCs
     */
    public List<EntityC> getEntityCs() {
        return entityCs;
    }

    /**
     * @param entityCs the entityCs to set
     */
    public void setEntityCs(List<EntityC> entityCs) {
        this.entityCs = entityCs;
    }

    /**
     * @return the d
     */
    public String getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(String d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return this.reflectionToStringBuilder.toString();
    }

}
