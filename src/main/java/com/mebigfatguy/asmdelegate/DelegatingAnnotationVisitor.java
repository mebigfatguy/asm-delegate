/*
 * asm-delegate - a set of asm visitors that allows multiple visitors to be used at the same time, simply
 *
 * Copyright 2019-2024 MeBigFatGuy.com
 * Copyright 2019-2024 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.asmdelegate;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.AnnotationVisitor;

public class DelegatingAnnotationVisitor extends AnnotationVisitor {

	private List<AnnotationVisitor> annotationVisitors;

	public DelegatingAnnotationVisitor(int api, List<AnnotationVisitor> visitors) {
		super(api);
		annotationVisitors = visitors;
	}

	@Override
	public void visit(String name, Object value) {
		for (AnnotationVisitor av : annotationVisitors) {
			if (av != null) {
				av.visit(name, value);
			}
		}
	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {
		for (AnnotationVisitor av : annotationVisitors) {
			if (av != null) {
				av.visitEnum(name, descriptor, value);
			}
		}
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String descriptor) {
		List<AnnotationVisitor> subAnnotationVisitors = new ArrayList(annotationVisitors.size());
		for (AnnotationVisitor av : annotationVisitors) {
			if (av != null) {
				AnnotationVisitor sav = av.visitAnnotation(name, descriptor);
				if (sav != null) {
					subAnnotationVisitors.add(sav);
				}
			}
		}

		if (subAnnotationVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, subAnnotationVisitors);
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		List<AnnotationVisitor> arrayAnnotationVisitors = new ArrayList(annotationVisitors.size());
		int i = 0;
		for (AnnotationVisitor av : annotationVisitors) {
			if (av != null) {
				AnnotationVisitor aav = av.visitArray(name);
				if (aav != null) {
					arrayAnnotationVisitors.add(aav);
				}
			}
		}

		if (arrayAnnotationVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, arrayAnnotationVisitors);
	}

	@Override
	public void visitEnd() {
		for (AnnotationVisitor av : annotationVisitors) {
			if (av != null) {
				av.visitEnd();
			}
		}
	}
}
