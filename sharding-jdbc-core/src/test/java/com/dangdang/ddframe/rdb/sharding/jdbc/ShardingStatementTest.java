/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.jdbc;

import com.dangdang.ddframe.rdb.integrate.db.AbstractShardingDataBasesOnlyDBUnitTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class ShardingStatementTest extends AbstractShardingDataBasesOnlyDBUnitTest {
    
    private ShardingDataSource shardingDataSource;
    
    @Before
    public void init() throws SQLException {
        shardingDataSource = getShardingDataSource();
    }
    
    @Test
    public void assertExecuteQuery() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteUpdate() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql), is(40));
        }
    }
    
    @Test
    public void assertExecute() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteQueryWithResultSetTypeAndResultSetConcurrency() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteQueryWithResultSetTypeAndResultSetConcurrencyAndResultSetHoldability() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteQueryWithResultSetHoldabilityIsZero() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, 0);
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteUpdateWithAutoGeneratedKeys() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql, Statement.NO_GENERATED_KEYS), is(40));
        }
    }
    
    @Test
    public void assertExecuteUpdateWithColumnIndexes() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql, new int[] {1}), is(40));
        }
    }
    
    @Test
    public void assertExecuteUpdateWithColumnNames() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql, new String[] {"orders_count"}), is(40));
        }
    }
    
    @Test
    public void assertExecuteWithAutoGeneratedKeys() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql, Statement.NO_GENERATED_KEYS));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteWithColumnIndexes() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql, new int[] {1}));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteWithColumnNames() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql, new String[] {"orders_count"}));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertGetConnection() throws SQLException {
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.getConnection(), is(connection));
        }
    }
    
    @Test
    public void assertGetGeneratedKeys() throws SQLException {
        String sql = "INSERT INTO `t_order`(`user_id`, `status`) VALUES (%d,'%s')";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertFalse(stmt.execute(String.format(sql, 1, "init")));
            assertFalse(stmt.getGeneratedKeys().next());
            assertFalse(stmt.execute(String.format(sql, 1, "init"), Statement.NO_GENERATED_KEYS));
            assertFalse(stmt.getGeneratedKeys().next());
            assertFalse(stmt.execute(String.format(sql, 1, "init"), Statement.RETURN_GENERATED_KEYS));
            assertTrue(stmt.getGeneratedKeys().next());
            assertThat(stmt.getGeneratedKeys().getLong(1), is(3L));
            assertFalse(stmt.execute(String.format(sql, 1, "init"), new int[]{1}));
            assertTrue(stmt.getGeneratedKeys().next());
            assertThat(stmt.getGeneratedKeys().getLong(1), is(4L));
            assertFalse(stmt.execute(String.format(sql, 1, "init"), new String[]{"user_id"}));
            assertTrue(stmt.getGeneratedKeys().next());
            assertThat(stmt.getGeneratedKeys().getLong(1), is(5L));
            assertFalse(stmt.execute(String.format(sql, 1, "init"), new int[]{2}));
            assertTrue(stmt.getGeneratedKeys().next());
            assertThat(stmt.getGeneratedKeys().getLong(1), is(6L));
            assertFalse(stmt.execute(String.format(sql, 1, "init"), new String[]{"no"}));
            assertTrue(stmt.getGeneratedKeys().next());
            assertThat(stmt.getGeneratedKeys().getLong(1), is(7L));
        }
    }
}
