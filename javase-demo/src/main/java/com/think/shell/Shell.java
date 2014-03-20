package com.think.shell;

import java.io.IOException;

public interface Shell {

	public Process exe(Command cmd)throws IOException;
}
