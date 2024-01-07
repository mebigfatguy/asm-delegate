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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;

public class DelegatingMethodVisitor extends MethodVisitor {

	private MethodVisitor[] methodVisitors;

	public DelegatingMethodVisitor(int api, MethodVisitor... visitors) {
		super(api);
		methodVisitors = visitors;
	}

	@Override
	public void visitParameter(String name, int access) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitParameter(name, access);
			}
		}
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i++] = mv.visitAnnotationDefault();
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i++] = mv.visitAnnotation(descriptor, visible);
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i++] = mv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitAnnotableParameterCount(parameterCount, visible);
			}
		}
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i++] = mv.visitParameterAnnotation(parameter, descriptor, visible);
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public void visitAttribute(Attribute attribute) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitAttribute(attribute);
			}
		}
	}

	@Override
	public void visitCode() {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitCode();
			}
		}
	}

	@Override
	public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitFrame(type, numLocal, local, numStack, stack);
			}
		}
	}

	@Override
	public void visitInsn(int opcode) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitInsn(opcode);
			}
		}
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitIntInsn(opcode, operand);
			}
		}
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitVarInsn(opcode, var);
			}
		}
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitTypeInsn(opcode, type);
			}
		}
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitFieldInsn(opcode, owner, name, descriptor);
			}
		}
	}

	@Override
	@Deprecated
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitMethodInsn(opcode, owner, name, descriptor);
			}
		}
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
			}
		}
	}

	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle,
			Object... bootstrapMethodArguments) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
			}
		}
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitJumpInsn(opcode, label);
			}
		}
	}

	@Override
	public void visitLabel(Label label) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitLabel(label);
			}
		}
	}

	@Override
	public void visitLdcInsn(Object value) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitLdcInsn(value);
			}
		}
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitIincInsn(var, increment);
			}
		}
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitTableSwitchInsn(min, max, dflt, labels);
			}
		}
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitLookupSwitchInsn(dflt, keys, labels);
			}
		}
	}

	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitMultiANewArrayInsn(descriptor, numDimensions);
			}
		}
	}

	@Override
	public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i] = mv.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitTryCatchBlock(start, end, handler, type);
			}
		}
	}

	@Override
	public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor,
			boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i] = mv.visitTryCatchAnnotation(typeRef, typePath, descriptor, visible);
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end,
			int index) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitLocalVariable(name, descriptor, signature, start, end, index);
			}
		}
	}

	@Override
	public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end,
			int[] index, String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[methodVisitors.length];
		int i = 0;
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				annotationVisitors[i] = mv.visitLocalVariableAnnotation(typeRef, typePath, start, end, index,
						descriptor, visible);
			}
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitLineNumber(line, start);
			}
		}
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitMaxs(maxStack, maxLocals);
			}
		}
	}

	@Override
	public void visitEnd() {
		for (MethodVisitor mv : methodVisitors) {
			if (mv != null) {
				mv.visitEnd();
			}
		}
	}

}
