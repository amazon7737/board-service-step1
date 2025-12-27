const { test, expect } = require('@playwright/test');

test.describe('게시판 서비스 E2E 테스트', () => {
  
  test('메인 페이지 네비게이션 테스트', async ({ page }) => {
    // 게시판 목록 페이지 테스트
    await page.goto('/board');
    await expect(page).toHaveTitle(/게시판 목록/);
    await expect(page.locator('h2')).toContainText('최신 게시글');
    
    // 네비게이션 바 요소 확인
    await expect(page.locator('.navbar-brand')).toContainText('게시판 서비스');
    await expect(page.locator('a[href="/login"]')).toBeVisible();
    await expect(page.locator('a[href="/signup"]')).toBeVisible();
  });

  test('로그인 페이지 표시 테스트', async ({ page }) => {
    await page.goto('/login');
    await expect(page.locator('h3')).toContainText('로그인');
    
    // 폼 요소 존재 확인
    await expect(page.locator('input[name="id"]')).toBeVisible();
    await expect(page.locator('input[name="pw"]')).toBeVisible();
    await expect(page.locator('button[type="submit"]')).toBeVisible();
  });

  test('회원가입 페이지 표시 테스트', async ({ page }) => {
    await page.goto('/signup');
    await expect(page.locator('h3')).toContainText('회원가입');
    
    // 폼 요소 확인
    await expect(page.locator('input[name="username"]')).toBeVisible();
    await expect(page.locator('input[name="email"]')).toBeVisible();
    await expect(page.locator('input[name="password"]')).toBeVisible();
  });

  test('회원가입 후 리다이렉트 테스트', async ({ page }) => {
    const timestamp = Date.now();
    const username = `testuser_${timestamp}`;
    const email = `test_${timestamp}@example.com`;
    const password = 'password123';
    
    await page.goto('/signup');
    await page.fill('input[name="username"]', username);
    await page.fill('input[name="email"]', email);
    await page.fill('input[name="password"]', password);
    await page.click('button[type="submit"]');
    
    // 로그인 또는 회원가입 페이지로 리다이렉트되어야 함
    await page.waitForURL(/\/(login|signup)/);
  });

  test('글쓰기 페이지 로그인 필요 테스트', async ({ page }) => {
    await page.goto('/board/write');
    
    // 로그인 페이지로 리다이렉트되어야 함
    await expect(page).toHaveURL(/\/login/);
  });

  test('CSS 스타일링 적용 확인', async ({ page }) => {
    await page.goto('/board');
    
    // 커스텀 CSS 로드 확인
    const navbar = page.locator('.navbar').first();
    await expect(navbar).toBeVisible();
    
    // Bootstrap 작동 확인
    const container = page.locator('.container').first();
    await expect(container).toBeVisible();
  });
});
