package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.*;

public class HashSetTest extends SetTest {

	@Override
	protected <T> Set<T> getSet() {
		return new HashSet<>(3);
	}
}
