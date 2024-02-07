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
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.TypePath;

public class DelegatingClassVisitor extends ClassVisitor {

	private ClassVisitor[] classVisitors;

	public DelegatingClassVisitor(int api, ClassVisitor... visitors) {
		super(api);
		this.classVisitors = visitors;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visit(version, access, name, signature, superName, interfaces);
			}
		}
	}

	@Override
	public void visitSource(String source, String debug) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitSource(source, debug);
			}
		}
	}

	@Override
	public ModuleVisitor visitModule(String name, int access, String version) {
		List<ModuleVisitor> moduleVisitors = new ArrayList<>(classVisitors.length);
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				ModuleVisitor mv = cv.visitModule(name, access, version);
				if (mv != null) {
					moduleVisitors.add(mv);
				}
			}
		}

		if (moduleVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingModuleVisitor(api, moduleVisitors);
	}

	@Override
	public void visitNestHost(String nestHost) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				if (cv != null) {
					cv.visitNestHost(nestHost);
				}
			}
		}
	}

	@Override
	public void visitOuterClass(String owner, String name, String descriptor) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitOuterClass(owner, name, descriptor);
			}
		}
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		List<AnnotationVisitor> annotationVisitors = new ArrayList<>(classVisitors.length);
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				AnnotationVisitor av = cv.visitAnnotation(descriptor, visible);
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
		List<AnnotationVisitor> annotationVisitors = new ArrayList<>(classVisitors.length);
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				AnnotationVisitor av = cv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
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
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitAttribute(attribute);
			}
		}
	}

	@Override
	public void visitNestMember(String nestMember) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitNestMember(nestMember);
			}
		}
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitInnerClass(name, outerName, innerName, access);
			}
		}
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
		List<FieldVisitor> fieldVisitors = new ArrayList(classVisitors.length);
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				FieldVisitor fv = cv.visitField(access, name, descriptor, signature, value);
				if (fv != null) {
					fieldVisitors.add(fv);
				}
			}
		}

		if (fieldVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingFieldVisitor(api, fieldVisitors);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
			String[] exceptions) {
		List<MethodVisitor> methodVisitors = new ArrayList<>(classVisitors.length);
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
				if (mv != null) {
					methodVisitors.add(mv);
				}
			}
		}

		if (methodVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingMethodVisitor(api, methodVisitors);
	}

	@Override
	public void visitPermittedSubclass(String permittedSubclass) {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitPermittedSubclass(permittedSubclass);
			}
		}
	}

	@Override
	public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
		List<RecordComponentVisitor> recordComponentVisitors = new ArrayList<>(classVisitors.length);
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				RecordComponentVisitor rcv = cv.visitRecordComponent(name, descriptor, signature);
				if (rcv != null) {
					recordComponentVisitors.add(rcv);
				}
			}
		}

		if (recordComponentVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingRecordComponentVisitor(api, recordComponentVisitors);
	}

	@Override
	public void visitEnd() {
		for (ClassVisitor cv : classVisitors) {
			if (cv != null) {
				cv.visitEnd();
			}
		}
	}
}
