/**
 * Copyright minsait by Indra Sistemas, S.A.
 * 2013-2018 SPAIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minsait.onesait.platform.controlpanel.rest.management.videobroker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.minsait.onesait.platform.controlpanel.rest.ManagementRestServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api
@ApiResponses({ @ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 500, message = "Internal server error"), @ApiResponse(code = 403, message = "Forbidden") })

public class VideobrokerRestService extends ManagementRestServices {

	@Autowired
	@Qualifier("globalCache")
	private HazelcastInstance hazelcastInstance;
	@Value("${onesaitplatform.videobroker.hazelcast.queue}")
	private String videoQueueName;

	@ApiOperation("Insert parameters")
	@PostMapping(VideobrokerUrl.OP_VIDEOBROKER)
	public ResponseEntity<?> insert(@ApiParam @RequestBody VideobrokerParameters parameters)
			throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		hazelcastInstance.getQueue(videoQueueName).offer(mapper.writeValueAsString(parameters));
		return new ResponseEntity<>(HttpStatus.OK);

	}

}