package org.tnmk.practicespringaws.pro05.datafactory

import org.tnmk.practicespringaws.pro05.ChildProto
import org.tnmk.practicespringaws.pro05.SampleComplicatedMessageProto

class SampleComplicatedProtoFactory {
    static SampleComplicatedMessageProto constructWithChildren(int numOfChildren) {
        SampleComplicatedMessageProto sampleMessageProto = SampleComplicatedMessageProto.newBuilder()
                .setValue("sample message value " + System.nanoTime())
                .addAllChildren(constructChildren(numOfChildren))
                .putChildrenMaps("child 1", constructChildProto())
                .putChildrenMaps("child 2", constructChildProto())
                .putChildrenMaps("child 3", constructChildProto())
                .build();
        return sampleMessageProto;
    }

    private static List<ChildProto> constructChildren(int numOfChildren) {
        List<ChildProto> children = new ArrayList<>();
        for (int i = 0; i < numOfChildren; i++) {
            children.add(constructChildProto());
        }
        return children;
    }

    private static ChildProto constructChildProto(){
        ChildProto childProto = ChildProto.newBuilder()
                .setId(System.nanoTime())
                .setValue("Child " + System.nanoTime())
                .build();
        return childProto;
    }
}
