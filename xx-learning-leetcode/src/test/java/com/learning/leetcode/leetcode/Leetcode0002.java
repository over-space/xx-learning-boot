package com.learning.leetcode.leetcode;


import com.learning.leetcode.leetcode.base.ListNode;
import com.learning.logger.BaseTest;
import org.junit.jupiter.api.Test;

/**
 * @author lifang
 * @since 2021/1/14
 */
public class Leetcode0002 extends BaseTest {


    @Test
    public void run() {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        addTwoNumbers(l1, l2);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {

            int v1 = l1 == null ? 0 : l1.val;
            int v2 = l2 == null ? 0 : l2.val;

            int sum = v1 + v2 + carry;

            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }

            carry = sum / 10;

            l1 = l1.next;
            l2 = l2.next;
        }

        if (carry > 0) {
            tail.next = new ListNode(carry);
        }

        return head;
    }


}
