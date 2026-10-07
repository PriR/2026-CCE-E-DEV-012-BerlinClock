package com.berlinclock.application;

import com.berlinclock.domain.model.TimeOfDay;

/** Output port: where the application learns what time it is now. */
public interface CurrentTime {

    TimeOfDay now();
}
