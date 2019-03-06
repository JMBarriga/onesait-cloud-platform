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
package com.minsait.onesait.platform.api.rule.rules;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.minsait.onesait.platform.api.rule.RuleManager;
import com.minsait.onesait.platform.api.service.ApiServiceInterface;

@Component
@Rule
public class CheckRequestBodyRule {

	@Priority
	public int getPriority() {
		return 1;
	}

	@Condition
	@SuppressWarnings("unchecked")
	public boolean existsRequest(Facts facts) {
		HttpServletRequest request = (HttpServletRequest) facts.get(RuleManager.REQUEST);
		Map<String, Object> data = (Map<String, Object>) facts.get(RuleManager.FACTS);
		Object body = data.get(ApiServiceInterface.BODY);
		if (request != null && body == null)
			return true;
		else
			return false;
	}

	@Action
	@SuppressWarnings("unchecked")
	public void setFirstDerivedData(Facts facts) throws IOException, ParseException {
		HttpServletRequest request = (HttpServletRequest) facts.get(RuleManager.REQUEST);
		Map<String, Object> data = (Map<String, Object>) facts.get(RuleManager.FACTS);

		String body = request.getReader().lines().collect(Collectors.joining());

		data.put(ApiServiceInterface.BODY, body);

	}

}