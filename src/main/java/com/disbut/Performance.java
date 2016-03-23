/*
 *   Copyright (c) 2016. The Acceml.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.disbut;

import com.disbut.measure.Measurement;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Acceml on 2016/3/22
 * Email: huminghit@gmail.com
 */
public class Performance<T> {

    private final Measurement measurement;

    private final RecordWriter recordWriter;

    private final List<Task> tasks = new LinkedList<>();

    private final CountDownLatch startTask = new CountDownLatch(1);

    private long startTimeNs;


    public Performance(Measurement measurement, RecordWriter recordWriter) {
        this.measurement = measurement;
        this.recordWriter = recordWriter;
    }

    public Performance<T> withTask(Task task) {
        tasks.add(task);
        return this;
    }

    public void go() {
        for (Task task : tasks) {
            task.thread.start();
        }
        startTimeNs = System.currentTimeMillis();
        measurement.setActivate();
        //startTask--;then the task will start.
        startTask.countDown();
    }

    public void waitAll() {
        for (Task task : tasks) {
            try {
                task.thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("failed to wait all tasks finished", e);
            }
        }
    }

    public void report() throws IOException {
        long elapsedTime = System.nanoTime() - startTimeNs;
        measurement.stop();
        recordWriter.write(elapsedTime);
    }

}

