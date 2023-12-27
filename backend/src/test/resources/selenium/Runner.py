from login_test import TestLogin
from register_test import TestRegister
from post_test import TestPost
from follow_test import TestFollow
from chat_test import TestChat
from HtmlTestRunner import HTMLTestRunner
import os
import unittest
import argparse

def run_login_test(report_path):
    report_name = 'functional_testing__test_login_report.txt'
    full_report_path = os.path.join(report_path, report_name)

    suite = unittest.TestSuite()
    suite.addTest(TestLogin('test_login_empty_password'))
    suite.addTest(TestLogin('test_login_invalid_password'))
    suite.addTest(TestLogin('test_login_invalid_username'))
    suite.addTest(TestLogin('test_login_empty_username'))
    suite.addTest(TestLogin('test_login_empty_password'))
    suite.addTest(TestLogin('test_login_success'))


    with open(full_report_path, 'w') as f:
        runner = HTMLTestRunner(stream=f, verbosity=2, report_name="test_login_report")
        result = runner.run(suite)
    print(result)

def run_register_test(report_path):
    report_name = 'functional_testing__test_register_report.txt'
    full_report_path = os.path.join(report_path, report_name)

    suite = unittest.TestSuite()
    suite.addTest(TestRegister("test_register_missing_first_name"))
    suite.addTest(TestRegister("test_register_missing_last_name"))
    suite.addTest(TestRegister("test_register_missing_username"))
    suite.addTest(TestRegister("test_register_missing_password"))
    suite.addTest(TestRegister("test_register_missing_password_confirm"))
    suite.addTest(TestRegister("test_register_success"))

    with open(full_report_path, "w") as f:
        runner = HTMLTestRunner(stream=f,verbosity=2,report_name="test_register_report")
        result = runner.run(suite)
    print(result)

def run_post_test(report_path):
    report_name = 'functional_testing__test_post_report.txt'
    full_report_path = os.path.join(report_path, report_name)

    suite = unittest.TestSuite()
    suite.addTest(TestPost("test_post_with_image_success"))
    suite.addTest(TestPost("test_post_without_text"))
    suite.addTest(TestPost("test_post_success"))

    with open(full_report_path, "w") as f:
        runner = HTMLTestRunner(stream=f,verbosity=2,report_name="test_post_report")
        result = runner.run(suite)
    print(result)

def run_follow_test(report_path):
    report_name = 'functional_testing__test_follow_report.txt'
    full_report_path = os.path.join(report_path, report_name)

    suite = unittest.TestSuite()
    suite.addTest(TestFollow("test_follow_success"))
    suite.addTest(TestFollow("test_follow_invalid_username"))
    suite.addTest(TestFollow("test_follow_empty_username"))

    with open(full_report_path, "w") as f:
        runner = HTMLTestRunner(stream=f,verbosity=2,report_name="test_follow_report")
        result = runner.run(suite)
    print(result)

def run_chat_test(report_path):
    report_name = 'functional_testing__test_chat_report.txt'
    full_report_path = os.path.join(report_path, report_name)

    suite = unittest.TestSuite()
    suite.addTest(TestChat("test_chat_success"))
    suite.addTest(TestChat("test_chat_empty_message"))

    with open(full_report_path, "w") as f:
        runner = HTMLTestRunner(stream=f,verbosity=2,report_name="test_chat_report")
        result = runner.run(suite)
    print(result)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Run login tests and generate HTML report.')
    parser.add_argument('report_path', help='Path where the HTML report will be saved.')

    args = parser.parse_args()
    run_login_test(args.report_path)
    """
    run_register_test(args.report_path)
    run_post_test(args.report_path)
    run_follow_test(args.report_path)
    run_chat_test(args.report_path)
    """
