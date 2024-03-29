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
import org.objectweb.asm.Attribute;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

public class DelegatingFieldVisitor extends FieldVisitor {

	private List<FieldVisitor> fieldVisitors;

	public DelegatingFieldVisitor(int api, List<FieldVisitor> visitors) {
		super(api);
		fieldVisitors = visitors;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		List<AnnotationVisitor> annotationVisitors = new ArrayList<>(fieldVisitors.size());
		for (FieldVisitor fv : fieldVisitors) {
			if (fv != null) {
				AnnotationVisitor av = fv.visitAnnotation(descriptor, visible);
				if (av != null) {
					annotationVisitors.add(av);
				}
			}
		}

		if (annotationVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
		List<AnnotationVisitor> annotationVisitors = new ArrayList<>(fieldVisitors.size());
		for (FieldVisitor fv : fieldVisitors) {
			if (fv != null) {
				AnnotationVisitor av = fv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
				if (av != null) {
					annotationVisitors.add(av);
				}
			}
		}

		if (annotationVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);

	}

	@Override
	public void visitAttribute(Attribute attribute) {
		for (FieldVisitor fv : fieldVisitors) {
			if (fv != null) {
				fv.visitAttribute(attribute);
			}
		}
	}

	@Override
	public void visitEnd() {
		for (FieldVisitor fv : fieldVisitors) {
			if (fv != null) {
				fv.visitEnd();
			}
		}
	}

}
