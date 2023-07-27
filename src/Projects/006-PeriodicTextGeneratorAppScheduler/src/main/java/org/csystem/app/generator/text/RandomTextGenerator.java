package org.csystem.app.generator.text;

import org.csystem.app.functional.IStringConsumer;
import org.csystem.util.scheduler.Scheduler;
import org.csystem.util.string.StringUtil;

import java.util.random.RandomGenerator;

public class RandomTextGenerator {
    private final RandomGenerator m_randomGenerator;
    private final Scheduler m_scheduler;
    private final int m_min, m_bound;
    private final int m_count;
    private int m_n;

    private void generateSchedulerCallback(IStringConsumer consumer)
    {
        var str = StringUtil.getRandomTextEN(m_randomGenerator, m_randomGenerator.nextInt(m_min, m_bound));
        consumer.accept(str);
        if (--m_n == 0)
            m_scheduler.cancel();
    }

    public RandomTextGenerator(RandomGenerator randomGenerator, int min, int bound, int count, long delay, long period)
    {
        m_randomGenerator = randomGenerator;
        m_scheduler = new Scheduler(delay, period);
        m_min = min;
        m_bound = bound;
        m_count = count;
    }

    public void generate(IStringConsumer consumer)
    {
        m_n = m_count;
        m_scheduler.schedule(() -> generateSchedulerCallback(consumer));
    }
}