/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.shardingproxy.backend.result.update;

import lombok.Getter;
import org.apache.shardingsphere.shardingproxy.backend.communication.jdbc.execute.response.ExecuteResponseUnit;
import org.apache.shardingsphere.shardingproxy.backend.communication.jdbc.execute.response.ExecuteUpdateResponseUnit;
import org.apache.shardingsphere.shardingproxy.backend.result.BackendResponse;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Update response.
 * 
 * @author zhangliang
 */
public final class UpdateResponse implements BackendResponse {
    
    private final List<Integer> updateCounts = new LinkedList<>();
    
    @Getter
    private final long lastInsertId;
    
    @Getter
    private long updateCount;
    
    public UpdateResponse(final Collection<ExecuteResponseUnit> responseUnits) {
        for (ExecuteResponseUnit each : responseUnits) {
            updateCount = ((ExecuteUpdateResponseUnit) each).getUpdateCount();
            updateCounts.add(((ExecuteUpdateResponseUnit) each).getUpdateCount());
        }
        lastInsertId = getLastInsertId(responseUnits);
    }
    
    private long getLastInsertId(final Collection<ExecuteResponseUnit> responseUnits) {
        long result = 0;
        for (ExecuteResponseUnit each : responseUnits) {
            result = Math.max(result, ((ExecuteUpdateResponseUnit) each).getLastInsertId());
        }
        return result;
    }
    
    /**
     * Merge updated counts.
     */
    public void mergeUpdateCount() {
        updateCount = 0;
        for (int each : updateCounts) {
            updateCount += each;
        }
    }
}