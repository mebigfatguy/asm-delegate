package com.mebigfatguy.asmdelegate;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.TypePath;

public class DelegatingRecordComponentVisitor extends RecordComponentVisitor {

	private List<RecordComponentVisitor> recordComponentVisitors;

	public DelegatingRecordComponentVisitor(int api, List<RecordComponentVisitor> visitors) {
		super(api);
		recordComponentVisitors = visitors;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		List<AnnotationVisitor> annotationVisitors = new ArrayList<>(recordComponentVisitors.size());
		for (RecordComponentVisitor rcv : recordComponentVisitors) {
			AnnotationVisitor av = rcv.visitAnnotation(descriptor, visible);
			if (av != null) {
				annotationVisitors.add(av);
			}
		}

		if (annotationVisitors.isEmpty()) {
			return null;
		}
		return new DelegatingAnnotationVisitor(api, annotationVisitors);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
		List<AnnotationVisitor> annotationVisitors = new ArrayList<>(recordComponentVisitors.size());
		for (RecordComponentVisitor rcv : recordComponentVisitors) {
			AnnotationVisitor av = rcv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
			if (av != null) {
				annotationVisitors.add(av);
			}
		}

		if (annotationVisitors.isEmpty()) {
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
