/*
 * Copyright (c) 2019 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.mskcc.cbio.portal.dao;

import org.mskcc.cbio.portal.model.*;
import java.sql.*;

/**
 * Data access object for Mutation table
 */
public final class DaoAlleleSpecificCopyNumber {

    public static long getLargestAscnId() throws DaoException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = JdbcUtil.getDbConnection(DaoAlleleSpecificCopyNumber.class);
            pstmt = con.prepareStatement("SELECT MAX(`ASCN_ID`) FROM `allele_specific_copy_number`");
            rs = pstmt.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            JdbcUtil.closeAll(DaoAlleleSpecificCopyNumber.class, con, pstmt, rs);
        }
    }
    
    public static int addAlleleSpecificCopyNumber(AlleleSpecificCopyNumber ascn) throws DaoException {
        if (!MySQLbulkLoader.isBulkLoad()) {
            throw new DaoException("You have to turn on MySQLbulkLoader in order to insert allele specific copy numbers");
        } else {
            int result = 1;
            MySQLbulkLoader.getMySQLbulkLoader("allele_specific_copy_number").insertRecord(
                String.valueOf(ascn.getAscnId()),
                Integer.toString(ascn.getAscnIntegerCopyNumber()),
                ascn.getAscnMethod(),
                Float.toString(ascn.getCcfMCopiesUpper()),
                Float.toString(ascn.getCcfMCopies()),
                Boolean.toString(ascn.getClonal()),
                Integer.toString(ascn.getMinorCopyNumber()),
                Integer.toString(ascn.getMutantCopies()),
                Integer.toString(ascn.getTotalCopyNumber()));
            return result;
        }
    }
}