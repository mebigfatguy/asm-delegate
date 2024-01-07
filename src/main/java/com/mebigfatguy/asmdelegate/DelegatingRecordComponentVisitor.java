package com.mebigfatguy.asmdelegate;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.TypePath;

public class DelegatingRecordComponentVisitor extends RecordComponentVisitor {

	private RecordComponentVisitor[] recordComponentVisitors;

	public DelegatingRecordComponentVisitor(int api, RecordComponentVisitor... visitors) {
		super(api);
		recordComponentVisitors = visitors;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[recordComponentVisitors.length];
		int i = 0;
		for (RecordComponentVisitor rcv : recordComponentVisitors) {
			annotationVisitors[i++] = rcv.visitAnnotation(descriptor, visible);
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
		AnnotationVisitor[] annotationVisitors = new AnnotationVisitor[recordComponentVisitors.length];
		int i = 0;
		for (RecordComponentVisitor rcv : recordComponentVisitors) {
			annotationVisitors[i++] = rcv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
		}

		if (i == 0) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public void visitAttribute(Attribute attribute) {
		for (RecordComponentVisitor rcv : recordComponentVisitors) {
			rcv.visitAttribute(attribute);
		}
	}

	@Override
	public void visitEnd() {
		for (RecordComponentVisitor rcv : recordComponentVisitors) {
			rcv.visitEnd();
		}
	}
}
