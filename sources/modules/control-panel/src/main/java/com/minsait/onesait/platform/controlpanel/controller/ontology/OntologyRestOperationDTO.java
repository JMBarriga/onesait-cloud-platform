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
package com.minsait.onesait.platform.controlpanel.controller.ontology;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class OntologyRestOperationDTO {

	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String type;
	@Getter
	@Setter
	private String origin;
	@Getter
	@Setter
	private String description;
	@Getter
	@Setter
	private List<OntologyRestOperationParamDTO> lParams;

	public OntologyRestOperationDTO(String name, String type, String origin, String description,
			List<OntologyRestOperationParamDTO> lParams) {
		super();
		this.name = name;
		this.type = type;
		this.origin = origin;
		this.description = description;
		this.lParams = lParams;
	}

}