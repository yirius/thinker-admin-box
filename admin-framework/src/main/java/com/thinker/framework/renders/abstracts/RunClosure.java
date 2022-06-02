package com.thinker.framework.renders.abstracts;

public interface RunClosure<T extends RootRender> {
    void run(T rootRender);
}
