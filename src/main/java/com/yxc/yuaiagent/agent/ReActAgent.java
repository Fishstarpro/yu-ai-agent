package com.yxc.yuaiagent.agent;

import lombok.Data;

/**
 * ClassName: RctAgent
 * Package: com.yxc.yuaiagent.agent
 *
 * @Author fishstar
 * @Create 2025/5/22 22:57
 * @Version 1.0
 * Description:
 * ReAct (Reasoning and Acting) 模式的代理抽象类
 * 实现了思考-行动的循环模式
 */

@Data
public abstract class ReActAgent extends BaseAgent {

    /**
     * 处理当前状态并决定下一步行动
     * @return 是否需要执行行动，true表示需要执行，false表示不需要执行
     */
    public abstract boolean think();

    /**
     * 执行决定的行动
     * @return 行动执行结果
     */
    public abstract String act();

    @Override
    public String step() {
        try {
            //是否执行
            boolean shouldAct = think();

            if (!shouldAct) {
                return "思考完成-无需执行";
            }

            return act();
        } catch (Exception e) {
            e.printStackTrace();

            return "步骤执行失败: " + e.getMessage();
        }
    }
}
