// menu.ts
export type MenuItem = {
  text: string;
  url?: string;           // 沒 children 才需要 url
  children?: MenuChild[];
};

export type MenuChild = {
  text: string;
  url: string;
};

export type MenuOptions = {
  mountSelector?: string;     // 預設 "#menu"
  menus: MenuItem[];
  openInNewTab?: boolean;     // 預設 false
};

const STYLE_ID = "shared-menu-style-v1";

function injectStyleOnce(): void {
  if (document.getElementById(STYLE_ID)) return;

  const style = document.createElement("style");
  style.id = STYLE_ID;
  style.textContent = `
    #menu{
      width: 180px;
      padding: 6px;
      border-right: 1px solid #ddd;
      background: #FFF7F0;
      display: flex;
      flex-direction: column;
      gap: 6px;
      box-sizing: border-box;
    }

    #menu .menu-box{
      display: flex;
      flex-direction: column;
      gap: 6px;
    }

    #menu .menu-btn{
      width: 100%;
      padding: 6px 6px;
      font-size: 14px;
      border-radius: 8px;
      background: #FFF7F0;
      border: 1px solid #e2d7cf;
      color: #222;
      text-align: left;
      cursor: pointer;
      box-sizing: border-box;
    }

    #menu .menu-btn:hover{
      background: #F6EDE5;
    }

    #menu .submenu{
      display: none;
      margin-left: 10px;
      padding-left: 8px;
      border-left: 2px solid #e2d7cf;
      gap: 6px;
      flex-direction: column;
    }

    #menu .submenu.open{
      display: flex;
    }

    #menu .submenu .menu-btn{
      font-size: 13px;
      padding: 5px 6px;
    }
  `;
  document.head.appendChild(style);
}

function escapeHtml(input: string): string {
  // 舊版 TS/瀏覽器也能用（不用 replaceAll）
  return input
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#39;");
}

function escapeAttr(input: string): string {
  // 給 HTML attribute 用（避免 url 裡有 " 之類弄壞標籤）
  return escapeHtml(input);
}

function buildMenuHTML(menus: MenuItem[]): string {
  return `
    <div class="menu-box">
      ${menus
        .map((m, idx) => {
          const title = escapeHtml(m.text);

          if (m.children && m.children.length > 0) {
            const childrenHTML = m.children
              .map((c) => {
                const cText = escapeHtml(c.text);
                const cUrl = escapeAttr(c.url ?? "");
                return `
                  <button class="menu-btn child-btn" type="button" data-url="${cUrl}">
                    ${cText}
                  </button>
                `;
              })
              .join("");

            return `
              <button class="menu-btn parent-btn" type="button" data-index="${idx}">
                ${title} ▼
              </button>
              <div class="submenu" id="submenu-${idx}">
                ${childrenHTML}
              </div>
            `;
          }

          const url = escapeAttr(m.url ?? "");
          return `
            <button class="menu-btn child-btn" type="button" data-url="${url}">
              ${title}
            </button>
          `;
        })
        .join("")}
    </div>
  `;
}

function bindEvents(mountEl: HTMLElement, openInNewTab: boolean): void {
  // 展開/收合（父按鈕）
  mountEl.querySelectorAll<HTMLButtonElement>(".parent-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      const idx = btn.getAttribute("data-index"); // ✅ 不用 dataset.index
      if (!idx) return;
      const box = document.getElementById(`submenu-${idx}`);
      box?.classList.toggle("open");
    });
  });

  // 子按鈕跳轉
  mountEl.querySelectorAll<HTMLButtonElement>(".child-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      const url = btn.getAttribute("data-url") ?? ""; // ✅ 不用 dataset.url
      if (!url) return;

      if (openInNewTab) {
        window.open(url, "_blank", "noopener,noreferrer");
      } else {
        window.location.href = url;
      }
    });
  });
}

/**
 * 在任何頁面呼叫這個就會渲染 menu
 */
export function initMenu(options: MenuOptions): void {
  const mountSelector = options.mountSelector ?? "#menu";
  const openInNewTab = options.openInNewTab ?? false;

  const run = () => {
    const mountEl = document.querySelector<HTMLElement>(mountSelector);
    if (!mountEl) return;

    injectStyleOnce();
    mountEl.innerHTML = buildMenuHTML(options.menus);
    bindEvents(mountEl, openInNewTab);
  };

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", run, { once: true });
  } else {
    run();
  }
}
