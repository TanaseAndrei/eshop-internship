package com.iquestgroup.strategies;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Class used to add prefixes to the tables' and columns' names.
 */
public class PrefixPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final String PREFIX_TABLE = "t_";
    private static final String PREFIX_COLUMN = "p_";

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        if (identifier == null) {
            return null;
        }

        final String newName = PREFIX_TABLE + identifier.getText();
        return Identifier.toIdentifier(newName);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (identifier == null) {
            return null;
        }

        final String newName = PREFIX_COLUMN + identifier.getText();
        return Identifier.toIdentifier(newName);
    }
}
